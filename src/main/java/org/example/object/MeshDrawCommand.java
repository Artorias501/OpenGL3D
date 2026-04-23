package org.example.object;

import org.example.object.base.Transform;

public class MeshDrawCommand {
    public int meshIndex = 0;
    Transform meshTransform = new Transform();

    public MeshDrawCommand() {
    }

    public MeshDrawCommand(int meshIndex) {
        this.meshIndex = meshIndex;
    }

    public MeshDrawCommand(int meshIndex, Transform meshTransform) {
        this.meshIndex = meshIndex;
        this.meshTransform = meshTransform;
    }
}
