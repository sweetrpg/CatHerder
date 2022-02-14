package com.sweetrpg.catherder.client.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class ScreenUtil {

    public static List<Component> splitInto(String text, int maxLength, Font font) {
        List<Component> list = new ArrayList<>();

        StringBuilder temp = new StringBuilder();
        String[] split = text.split(" ");

        for (int i = 0; i < split.length; ++i) {
            String str = split[i];
            int length = font.width(temp + str);

            if (length > maxLength) {
                list.add(new TextComponent(temp.toString()));
                temp = new StringBuilder();
            }

            temp.append(str);
            temp.append(" ");

            if (i == split.length - 1) {
                list.add(new TextComponent(temp.toString()));
            }
        }

        return list;
    }


}
