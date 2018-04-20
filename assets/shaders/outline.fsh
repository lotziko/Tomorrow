#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 onePixel;

void main() {
	
	float alpha = texture2D( u_texture, v_texCoords ).a;
	float constAlpha = alpha;
	
	vec4 pixel = texture2D(u_texture, v_texCoords);
	vec3 color = mix(vec3(0.32, 0.375, 0.324), pixel.rgb, 0.9);
    gl_FragColor = vec4(color, pixel.a);
	
    alpha += ceil(texture2D( u_texture, v_texCoords + onePixel * vec2(1.0, 0.0)).a);
    alpha += ceil(texture2D( u_texture, v_texCoords - onePixel * vec2(1.0, 0.0)).a);
    alpha += ceil(texture2D( u_texture, v_texCoords + onePixel * vec2(0.0, 1.0)).a);
    alpha += ceil(texture2D( u_texture, v_texCoords - onePixel * vec2(0.0, 1.0)).a);
	
	if (constAlpha == 0.0) {
		gl_FragColor.rgba = vec4(0.32, 0.375, 0.324, sign(alpha)/2);
	}
}