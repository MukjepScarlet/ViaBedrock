/*
 * This file is part of ViaBedrock - https://github.com/RaphiMC/ViaBedrock
 * Copyright (C) 2023-2024 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.viabedrock.protocol.rewriter.resourcepack;

import com.viaversion.viaversion.libs.gson.JsonObject;
import net.raphimc.viabedrock.api.model.resourcepack.ResourcePack;
import net.raphimc.viabedrock.api.model.resourcepack.TextureDefinitions;
import net.raphimc.viabedrock.protocol.storage.ResourcePacksStorage;

import java.awt.image.BufferedImage;
import java.util.Map;

public class CustomItemTextureResourceRewriter extends ItemModelResourceRewriter {

    public static final String ITEM = "paper";

    public CustomItemTextureResourceRewriter() {
        super(ITEM, "item");
    }

    @Override
    protected void apply(final ResourcePacksStorage resourcePacksStorage, final ResourcePack.Content javaContent, final Map<Integer, JsonObject> overridesMap) {
        for (Map.Entry<String, TextureDefinitions.ItemTextureDefinition> entry : resourcePacksStorage.getTextures().itemTextures().entrySet()) {
            for (ResourcePack pack : resourcePacksStorage.getPackStackTopToBottom()) {
                final ResourcePack.Content bedrockContent = pack.content();
                final BufferedImage texture = bedrockContent.getShortnameImage(entry.getValue().texturePath());
                if (texture == null) continue;

                javaContent.putImage("assets/viabedrock/textures/" + this.getJavaTexturePath(entry.getValue().texturePath()) + ".png", texture);

                final JsonObject itemModel = new JsonObject();
                itemModel.addProperty("parent", "minecraft:item/generated");
                final JsonObject layer0 = new JsonObject();
                layer0.addProperty("layer0", "viabedrock:" + this.getJavaTexturePath(entry.getValue().texturePath()));
                itemModel.add("textures", layer0);
                javaContent.putJson("assets/viabedrock/models/" + this.getJavaModelName(entry.getKey()) + ".json", itemModel);
                this.addOverride(overridesMap, entry.getKey());
                break;
            }
        }
    }

}
