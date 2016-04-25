// Unknown Pleasures
// Original version by @simesgreen
// Slightly modified by PA Mudry

// What we get from Java, *once*
uniform vec2 center;
uniform vec2 resolution;
uniform float time;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform

float hash( float n )
{
    return fract(sin(n)*4358.5453);
}
 
float fade(float t)
{     
     return t * t * (3.0 - 2.0 * t);
}
		    
// 1d noise
float noise(float x)
{
   float p = floor(x);
   float f = fract(x);
   f = fade(f);
   float r = mix(hash(p), hash(p+1.0), f);
   r = r*r;
   return r;
}

// 2d noise
float noise(vec2 x )
{
    vec2 p = floor(x);
    vec2 f = fract(x);
    f = f*f*(3.0-2.0*f);

    float n = p.x + p.y*57.0;
    float res = mix(mix( hash(n+  0.0), hash(n+  1.0),f.x),
                    mix( hash(n+ 57.0), hash(n+ 58.0),f.x), f.y);
    res = res*res;
    return res;
}

float fbm(float x)
{
    return noise(x)*0.5 +
	   noise(x*2.0)*0.25 +
	   noise(x*4.0)*0.125 +
	   noise(x*8.0)*0.0625;
}

float fbm(vec2 x)
{
    return noise(x)*0.5 +
	   noise(x*2.0)*0.25 +
	   noise(x*4.0)*0.125 +
	   noise(x*8.0)*0.0625;
}

void main(void)
{
    const float linew = 0.004;
    const int waves = 45;
    const float freq = 5.0;
	const float offsetY = 0.9;
	const float backgroundWhiteness = 0.3;	
	const float lineSeparation = 0.04;
	
    vec2 p = (gl_FragCoord.xy / resolution.xy)*2.5-1.25;
    p.x *= resolution.x/resolution.y;
    	    
    float w = 0.05 + smoothstep(0.7, 0.3, abs(p.x))*0.4;    	
    float c = backgroundWhiteness;
    float yp = -1.0;
	
    // check waves bottom to top
    for(int i=0; i<waves; i++) {
		float y = -offsetY + fbm( vec2(p.x*freq + float(i)*10.0 + time*0.3, time*0.3)) * w + float(i)*lineSeparation;
    	c += smoothstep(linew, 0.0, abs(y - p.y)) * ((y > yp) ? 1.0 : 0.0);
		yp = max(y, yp);
    }
 		
    gl_FragColor = vec4(vec3(c),1.0);
}
