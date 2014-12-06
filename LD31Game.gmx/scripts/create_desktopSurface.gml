//script_createDesktopSurface()
global.desktopSurface = surface_create(global.backgroundWidth, global.backgroundHeight);
surface_set_target(global.desktopSurface);
draw_background(global.backgroundImage, 0,0);
surface_reset_target();