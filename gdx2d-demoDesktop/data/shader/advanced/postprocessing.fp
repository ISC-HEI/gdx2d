// in from the java side
uniform sampler2D backbuffer;
uniform vec2 resolution;
uniform float time;
uniform int enabled;

//"in" varyings from our vertex shader
varying vec4 vColor;
varying vec2 vTexCoord;

const float RADIUS = 0.3;
const float SOFTNESS = 0.25;

void main() {
	//sample the texture
	vec2 position = ( gl_FragCoord.xy / resolution.xy );
	vec4 me = texture2D(backbuffer, position);

	// determines the center of the screen
	vec2 center = (gl_FragCoord.xy / resolution.xy) - vec2(0.5, 0.5);

	//determine the vector length from center
	float len = length(center);
	float r = abs(sin(time) * RADIUS) + 0.3; // Change the radius with time
	float vignette = smoothstep(r, r - SOFTNESS, len);

	//apply our vignette
	if(enabled == 1){
		me.rgb *= vignette;
		me.rgba = me.rgga;
	}

	//final color
	gl_FragColor = me.rgba;
}

