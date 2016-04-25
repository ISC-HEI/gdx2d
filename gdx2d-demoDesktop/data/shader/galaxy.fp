// Parallax scrolling fractal galaxy.
// Inspired by JoshP's Simplicity shader: https://www.shadertoy.com/view/lslGWr
// http://www.fractalforums.com/new-theories-and-research/very-simple-formula-for-fractal-patterns/
// Ported from ShaderToys.com by redexe@gmail.com

// What we get from Java, *once*
uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform
in vec2 vSurfacePosition;

float field(vec3 p) {
	float strength = 7.0;
	float accum = 0.0;
	float prev = 0.0;
	float tw = 0.0;
	for (int i = 0; i < 25; i++) {
		float mag = dot(p, p);
		p = abs(p) / mag + vec3(-0.5, -0.4, -1.5);
		float w = exp(-float(i) / 7.0);
		accum += w * exp(-strength * pow(abs(mag - prev), 2.2));
		tw += w;
		prev = mag;
	}
	return max(0.0, 5.0 * accum / tw - 0.7);
}

void main() {
    vec2 uv = 2.0 * gl_FragCoord.xy / resolution.xy - 1.0;
	vec2 uvs = uv * resolution.xy / max(resolution.x, resolution.y);
	vec3 p = vec3(uvs / 4.0, 0) + vec3(1., -1.3, 0.0);
	p += 0.2 * vec3(sin(1.0 / 16.0), sin(1.0 / 12.0),  sin(time / 128.0));
	
	float t = field(p);
	float v = (1.0 - exp((abs(uv.x) - 1.0) * 6.)) * (1.0 - exp((abs(uv.y) - 1.0) * 6.0));
	
	gl_FragColor = mix(0.4, 1.0, v) * vec4(1.8 * t * t * t, 1.4 * t * t, t, 1.0);
}