/**
 * Solution from http://stackoverflow.com/questions/2797549/block-filters-using-fragment-shaders
 * IMHO, this is probably slow as hell
 */

// IN
varying vec4 vColor;
varying vec2 vTexCoord;
uniform sampler2D u_texture;
uniform int matrix;

const float width = 256.0;
const float height = 256.0;

const mat3 SobelVert= mat3( 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, -1.0, -2.0, -1.0 );
const mat3 SobelHorz= mat3( 1.0, 0.0, -1.0, 2.0, 0.0, -2.0, 1.0, 0.0, -1.0 );
const mat3 SimpleBlur= (1.0/9.0)*mat3( 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 );
const mat3 Sharpen= mat3( 0.0, -1.0, 0.0, -1.0, 5.0, -1.0, 0.0, -1.0, 0.0 );
const mat3 GaussianBlur= (1.0/16.0)*mat3( 1.0, 2.0, 1.0, 2.0, 4.0, 2.0, 1.0, 2.0, 1.0 );
const mat3 SimpleHorzEdge= mat3( 0.0, 0.0, 0.0, -3.0, 3.0, 0.0, 0.0, 0.0, 0.0 );
const mat3 SimpleVertEdge= mat3( 0.0, -3.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0 );
const mat3 ClearNone= mat3( 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 );
 
void main(void)
{
	// The active matrix
	mat3 activeMatrix = GaussianBlur;
	
	if(matrix == 1)
		activeMatrix = SobelVert;
	
	if(matrix == 2)
		activeMatrix = SobelHorz;
		
	if(matrix == 3)
		activeMatrix = Sharpen;

	if(matrix == 4)
		activeMatrix = SimpleHorzEdge;		
	
	if(matrix == 5)
		activeMatrix = SimpleVertEdge;

   vec4 sum = vec4(0.0);

   if(vTexCoord.x < 0.5)
   {
      mat3 I, R, G, B;
      vec3 sample;

      // fetch the 3x3 neighbourhood and use the RGB vector's length as intensity value
      for (int i=0; i<3; i++){
        for (int j=0; j<3; j++) {
          sample = texture2D(u_texture, vTexCoord.xy + vec2(i-1,j-1)/vec2(width, height)).rgb;
          I[i][j] = length(sample); //intensity (or illumination)
          R[i][j] = sample.r; 
          G[i][j] = sample.g;
          B[i][j] = sample.b;  
        }
      }

      //apply the kernel convolution
      mat3 convolvedMatR = matrixCompMult( activeMatrix, R);
      mat3 convolvedMatG = matrixCompMult( activeMatrix, G);
      mat3 convolvedMatB = matrixCompMult( activeMatrix, B);
           
      float convR = 0.0;
      float convG = 0.0;
      float convB = 0.0;
      
      //sum the result
      for (int i=0; i<3; i++){
        for (int j=0; j<3; j++) {
          convR += convolvedMatR[i][j];
          convG += convolvedMatG[i][j];
          convB += convolvedMatB[i][j];
        }
      }
      
      sum = vec4(vec3(convR, convG, convB), 1.0);
  }
   else if( vTexCoord.x >0.51 )
   {
        sum = texture2D(u_texture, vTexCoord.xy);
   }
   else // Draw a red line
   {
        sum = vec4(1.0, 0.0, 0.0, 1.0);
   }

   gl_FragColor = sum;
}