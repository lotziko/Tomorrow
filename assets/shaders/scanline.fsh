#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 onePixel;

void main() {
	gl_FragColor = v_color * texture2D( u_texture, v_texCoords );
	
	float fmod = mod(gl_FragCoord.y, 2.0)/50.0;
	gl_FragColor.a -= fmod + 0.05;
}