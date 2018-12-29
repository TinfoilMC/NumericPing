/*
 * MIT License
 *
 * Copyright (c) 2018 creeper123123321 and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.creeper123123321.numericping.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiTabOverlay extends Gui {
    /**
     * @author creeper123123321
     */
    @Overwrite
    protected void drawPing(int x1, int x2, int y, NetworkPlayerInfo playerInfo) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int ping = playerInfo.getResponseTime();
        int rgb;
        if (ping < 0) rgb = 0x0000FF; // Blue
        else if (ping < 150) rgb = 0x00FF00; // Green
        else if (ping < 300) rgb = 0x00AA00; // Dark green
        else if (ping < 600) rgb = 0xFFFF00; // Yellow
        else if (ping < 1000) rgb = 0xFF0000; // Red
        else rgb = 0xAA0000; // Dark red

        String pingString = toSubscriptNumbers(Integer.toString(ping));

        this.zLevel += 100.0F;
        this.drawString(
                Minecraft.getInstance().fontRenderer,
                pingString,
                x1 + x2 - Minecraft.getInstance().fontRenderer.getStringWidth(pingString),
                y,
                rgb
        );
        this.zLevel -= 100.0F;
    }

    private String toSubscriptNumbers(String string) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                chars[i] += '₀' - '0';
            } else if (chars[i] == '-') {
                chars[i] = '₋';
            }
        }
        return new String(chars);
    }
}
