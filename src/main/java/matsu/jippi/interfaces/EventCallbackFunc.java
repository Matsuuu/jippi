package matsu.jippi.interfaces;

import matsu.jippi.enumeration.slpreader.Command;

public interface EventCallbackFunc {

    public boolean callback(Command command, EventPayloadTypes payload);

}
