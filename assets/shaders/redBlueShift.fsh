#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float offset;
uniform float scaleFactor;

void main() {

	vec2 coord = vec2(v_texCoords.x, v_texCoords.y);
	coord.x += (mod(v_texCoords.y * 256, 2.0) / 256.0) * offset;
	
	vec4 c1 = texture2D(u_texture, coord - vec2(offset * scaleFactor, 0));
	vec4 c2 = texture2D(u_texture, coord);
	vec4 c3 = texture2D(u_texture, coord + vec2(offset * scaleFactor, 0));
	
	gl_FragColor = vec4(c1.r, c2.g, c3.b, max(c1.a, max(c2.a, c3.a))) * v_color;
	
}