/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.modules.regions;

import org.bukkit.event.Listener;

/**
 *
 * @author Joe
 */
public class PlayerEventListener implements Listener 
{
    
    private RegionModule regionModule;

    public PlayerEventListener(RegionModule regionModule) {
        this.regionModule = regionModule;
    }
    
}
