/*
 * This file is part of ViaBedrock - https://github.com/RaphiMC/ViaBedrock
 * Copyright (C) 2023-2025 RK_01/RaphiMC and contributors
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
package net.raphimc.viabedrock.protocol.rewriter.blockentity;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import net.raphimc.viabedrock.api.chunk.BedrockBlockEntity;
import net.raphimc.viabedrock.protocol.rewriter.BlockEntityRewriter;

public class BrushableBlockBlockEntityRewriter implements BlockEntityRewriter.Rewriter {

    @Override
    public BlockEntity toJava(UserConnection user, BedrockBlockEntity bedrockBlockEntity) {
        final CompoundTag bedrockTag = bedrockBlockEntity.tag();
        final CompoundTag javaTag = new CompoundTag();

        if (bedrockTag.get("brush_direction") instanceof ByteTag brushDirectionTag) {
            final byte brushDirection = brushDirectionTag.asByte();
            if (brushDirection >= 0 && brushDirection <= 5) {
                javaTag.putInt("hit_direction", brushDirection);
            }
        }
        this.copyItem(user, bedrockTag, javaTag, "item");
        this.copy(bedrockTag, javaTag, "LootTable", StringTag.class);
        this.copy(bedrockTag, javaTag, "LootTableSeed", IntTag.class);

        return new BlockEntityImpl(bedrockBlockEntity.packedXZ(), bedrockBlockEntity.y(), -1, javaTag);
    }

}
