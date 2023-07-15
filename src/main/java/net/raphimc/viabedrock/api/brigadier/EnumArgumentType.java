/*
 * This file is part of ViaBedrock - https://github.com/RaphiMC/ViaBedrock
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
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
package net.raphimc.viabedrock.api.brigadier;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.raphimc.viabedrock.protocol.model.CommandData;

import java.util.concurrent.CompletableFuture;

public class EnumArgumentType implements ArgumentType<Object> {

    private static final SimpleCommandExceptionType INVALID_ENUM_EXCEPTION = new SimpleCommandExceptionType(new LiteralMessage("Invalid enum"));

    private final CommandData.EnumData enumData;

    public EnumArgumentType(final CommandData.EnumData enumData) {
        this.enumData = enumData;
    }

    public static EnumArgumentType enumData(final CommandData.EnumData enumData) {
        return new EnumArgumentType(enumData);
    }

    public Object parse(StringReader reader) throws CommandSyntaxException {
        final String s = reader.readUnquotedString();
        if (!this.enumData.values().containsKey(s)) {
            throw INVALID_ENUM_EXCEPTION.createWithContext(reader);
        }

        return null;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SuggestionsUtil.suggestMatching(this.enumData.values().keySet(), builder);
    }

}
