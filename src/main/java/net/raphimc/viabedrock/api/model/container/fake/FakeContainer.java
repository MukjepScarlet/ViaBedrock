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
package net.raphimc.viabedrock.api.model.container.fake;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.mcstructs.text.TextComponent;
import net.raphimc.viabedrock.api.model.container.Container;
import net.raphimc.viabedrock.api.util.PacketFactory;
import net.raphimc.viabedrock.protocol.data.enums.bedrock.ContainerType;
import net.raphimc.viabedrock.protocol.model.BedrockItem;
import net.raphimc.viabedrock.protocol.storage.InventoryTracker;

public abstract class FakeContainer extends Container {

    public FakeContainer(final UserConnection user, final ContainerType type, final TextComponent title) {
        super(user, user.get(InventoryTracker.class).getNextFakeContainerId(), type, title, null, 0);
    }

    @Override
    public BedrockItem getItem(final int slot) {
        throw new UnsupportedOperationException("Fake containers cannot have bedrock items");
    }

    @Override
    public BedrockItem[] getItems() {
        throw new UnsupportedOperationException("Fake containers cannot have bedrock items");
    }

    @Override
    public boolean setItem(final int slot, final BedrockItem item) {
        throw new UnsupportedOperationException("Fake containers cannot have bedrock items");
    }

    @Override
    public boolean setItems(final BedrockItem[] items) {
        throw new UnsupportedOperationException("Fake containers cannot have bedrock items");
    }

    public void onAnvilRename(final String name) {
    }

    public void onClosed() {
    }

    public void close() {
        PacketFactory.sendJavaContainerClose(this.user, this.javaContainerId());
        this.user.get(InventoryTracker.class).markPendingClose(this);
    }

}
