#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 sourceSize;
uniform vec2 targetSize;

void main() {
	
	float prescale = floor(targetSize.y / sourceSize.y);
    vec2 texel = v_texCoords.xy * sourceSize.xy;
    vec2 texel_floored = floor(texel);
    vec2 s = fract(texel);
    float region_range = 0.5 - 0.5 / prescale;

    // Figure out where in the texel to sample to get correct pre-scaled bilinear.
    // Uses the hardware bilinear interpolator to avoid having to sample 4 times manually.

    vec2 center_dist = s - 0.5;
    vec2 f = (center_dist - clamp(center_dist, -region_range, region_range)) * prescale + 0.5;

    vec2 mod_texel = texel_floored + f;
    
    gl_FragColor = texture2D(u_texture, mod_texel / sourceSize.xy) * v_color;
}