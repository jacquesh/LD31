///script_createEnemy()
if(ds_priority_size(global.enemySpawnLocQueue) == 0)
{
    return -1;
}

var enemyLoc = ds_priority_delete_max(global.enemySpawnLocQueue);
var xIndex = enemyLoc[0];
var yIndex = enemyLoc[1];

var spawnX = xIndex*global.blockSize;
var spawnY = yIndex*global.blockSize;

// Add neighbouring blocks to the queue
for(var offX=-1; offX<=1; offX++)
{
    for(var offY=-1; offY<=1; offY++)
    {
        if(offX == 0 && offY == 0)
            continue;
            
        var newLoc;
        newLoc[0] = xIndex+offX;
        newLoc[1] = yIndex+offY;
        if(newLoc[0] < 0 || newLoc[0] >= global.xBlocks || newLoc[1] < 0 || newLoc[1] >= global.yBlocks)
            continue;

        if(!global.blockExists[newLoc[1], newLoc[0]])
            continue;   
        var existCheck = ds_priority_find_priority(global.enemySpawnLocQueue, newLoc);
        if(!is_undefined(existCheck))
            continue;
        ds_priority_add(global.enemySpawnLocQueue, newLoc, newLoc[0]+newLoc[1]);        
    }
}
global.blockExists[yIndex, xIndex] = false;

// Draw emptiness where the object will spawn
var newEmptiness = instance_create(spawnX, spawnY, obj_emptinessCollider);
newEmptiness.sprite_index = global.emptinessSprite;

// Create the sprite for the new object
if(!surface_exists(global.desktopSurface))
{
    script_createDesktopSurface();
}
var newSprite = sprite_create_from_surface(global.desktopSurface,
                                        spawnX, spawnY,
                                        global.blockSize,global.blockSize,
                                        false, false,
                                        blockSize/2, blockSize/2);

// Actually create the new object                                        
var newInst = instance_create(spawnX + (global.blockSize/2),
                          spawnY+(global.blockSize/2),
                          obj_enemy);
newInst.sprite_index = newSprite;
newInst.mask_index = newSprite;

return newInst;
