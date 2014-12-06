///script_createEmptinessTemplateSurface
global.emptinessTemplateSurface = surface_create(global.blockSize,
                                                 global.blockSize);
surface_set_target(global.emptinessTemplateSurface);
draw_clear(c_black);
surface_reset_target();