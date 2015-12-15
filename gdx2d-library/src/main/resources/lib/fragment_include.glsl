#version 110

#if __VERSION__ < 130
   #define in varying
   #define texture texture2D
#endif


#pragma debug(on)
#pragma optimize(off)

#ifdef GL_ES
	precision mediump float;
#endif