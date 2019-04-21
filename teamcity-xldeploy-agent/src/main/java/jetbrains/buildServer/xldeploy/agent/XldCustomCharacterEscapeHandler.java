package jetbrains.buildServer.xldeploy.agent;

import java.io.IOException;
import java.io.Writer;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public  class XldCustomCharacterEscapeHandler implements CharacterEscapeHandler {
    @Override
    public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
        out.write(ch, start, length);
    }
}
