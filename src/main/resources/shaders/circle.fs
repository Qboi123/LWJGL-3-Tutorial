#ifdef GL_ES
precision lowp float;
#endif

uniform vec2 u_resolution;
uniform vec2 position;
uniform float radius;

float draw_circle(vec2 coord, float radius) {
    return step(length(coord), radius);
}

void main() {
    vec2 coord = gl_FragCoord.xy / u_resolution;
    float circle = draw_circle(position, radius);
    vec3 color = vec3(circle);

    gl_FragColor = vec4(color, 1.0);
}
