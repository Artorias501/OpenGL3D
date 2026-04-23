#version 330 core

in vec3 vertexColor;
in vec3 normal;

out vec4 FragColor;

uniform vec3 directLight;

void main() {
    vec3 N = normalize(normal);
    vec3 L = normalize(directLight);

    float diff = max(dot(N, L), 0.0);

    // 环境光
    float ambient = 0.2;
    vec3 finalColor = vertexColor * (ambient + diff);

    FragColor = vec4(finalColor, 1.0);
}