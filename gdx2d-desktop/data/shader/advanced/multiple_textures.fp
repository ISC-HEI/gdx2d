uniform sampler2D texture0; // Current frame
uniform sampler2D texture1; // Next frame

uniform vec2 resolution; // Stores the dimension of the fragment shader
uniform vec2 mouse; // This information is given from the Java side. 
uniform float time;
uniform int textureChosen;

//"in" varyings from our vertex shader
varying vec4 vColor;
varying vec2 vTexCoord;

void main() {
	vec3 sample;
	if(textureChosen == 1)
		sample = (gl_FragCoord.xy / resolution.xy).y > 0.5? texture2D(texture0, vTexCoord.xy).rgb : texture2D(texture1, vTexCoord.xy).rgb;
	else{
		sample = (gl_FragCoord.xy / resolution.xy).y > 0.5? texture2D(texture1, vTexCoord.xy).rgb : texture2D(texture0, vTexCoord.xy).rgb;
	}

	gl_FragColor = vec4(sample, 1.0);
}

