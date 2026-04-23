package org.example.object;

import org.example.object.base.Mesh;
import org.example.object.base.Transform;
import org.example.object.base.Vertex;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public class GLBFileReader {
    public static GLRenderObject load(String fileName, int shaderProgram) {
        AIScene scene = aiImportFile(
                fileName,
                aiProcess_Triangulate |
                        aiProcess_FlipUVs |
                        aiProcess_GenNormals |
                        aiProcess_CalcTangentSpace
        );
        if (scene == null || scene.mRootNode() == null)
            throw new RuntimeException("glb load error: " + aiGetErrorString());

        GLRenderObject glRenderObject = new GLRenderObject(shaderProgram);
        glRenderObject.meshes = loadMeshes(scene);

        List<MeshDrawCommand> meshDrawCommands = new ArrayList<>();
        handleNode(scene.mRootNode(), scene, new Matrix4f(), meshDrawCommands);
        glRenderObject.meshDrawCommands = meshDrawCommands;

        aiReleaseImport(scene);
        return glRenderObject;
    }

    private static List<GLMesh> loadMeshes(AIScene scene) {
        int meshCount = scene.mNumMeshes();
        PointerBuffer meshBuffer = scene.mMeshes();

        List<GLMesh> meshes = new ArrayList<>();

        for (int i = 0; i < meshCount; i++) {
            AIMesh aiMesh = AIMesh.create(meshBuffer.get(i));
            GLMesh mesh = new GLMesh(convertAIMesh(aiMesh));
            meshes.add(mesh);
        }

        return meshes;
    }

    private static Mesh convertAIMesh(AIMesh mesh) {
        List<Vertex> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        int vertexCount = mesh.mNumVertices();

        AIVector3D.Buffer positions = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();
        AIVector3D.Buffer texCoords = mesh.mTextureCoords(0);

        for (int i = 0; i < vertexCount; i++) {
            AIVector3D pos = positions.get(i);

            Vertex v = new Vertex(
                    new Vector3f(pos.x(), pos.y(), pos.z())
            );

            // normals
            if (normals != null) {
                AIVector3D n = normals.get(i);
                v.normals.set(n.x(), n.y(), n.z());
            }

            // uv
            if (texCoords != null) {
                AIVector3D uv = texCoords.get(i);
                v.textureCoords.set(uv.x(), uv.y());
            }

            vertices.add(v);
        }

        // indices
        int faceCount = mesh.mNumFaces();
        AIFace.Buffer faces = mesh.mFaces();

        for (int i = 0; i < faceCount; i++) {
            AIFace face = faces.get(i);
            IntBuffer idx = face.mIndices();

            while (idx.remaining() > 0) {
                indices.add(idx.get());
            }
        }

        return new Mesh(vertices, indices);
    }

    private static void handleNode(
            AINode node,
            AIScene scene,
            Matrix4f parentTransform,
            List<MeshDrawCommand> drawCommands
    ) {
        // 当前 node transform
        Matrix4f localTransform = toMatrix(node.mTransformation());

        Matrix4f globalTransform = new Matrix4f(parentTransform).mul(localTransform);

        // 处理 mesh 引用
        int meshCount = node.mNumMeshes();
        IntBuffer meshIndices = node.mMeshes();

        for (int i = 0; i < meshCount; i++) {
            int meshIndex = meshIndices.get(i);

            MeshDrawCommand cmd = new MeshDrawCommand();
            cmd.meshIndex = meshIndex;
            cmd.meshTransform = new Transform(globalTransform);

            drawCommands.add(cmd);
        }

        // 递归子节点
        int childCount = node.mNumChildren();
        PointerBuffer children = node.mChildren();

        for (int i = 0; i < childCount; i++) {
            handleNode(
                    AINode.create(children.get(i)),
                    scene,
                    globalTransform,
                    drawCommands
            );
        }
    }

    private static Matrix4f toMatrix(AIMatrix4x4 m) {
        return new Matrix4f(
                m.a1(), m.b1(), m.c1(), m.d1(),
                m.a2(), m.b2(), m.c2(), m.d2(),
                m.a3(), m.b3(), m.c3(), m.d3(),
                m.a4(), m.b4(), m.c4(), m.d4()
        );
    }
}
