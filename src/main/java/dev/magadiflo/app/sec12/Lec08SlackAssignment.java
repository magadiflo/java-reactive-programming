package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec12.assignment.SlackMember;
import dev.magadiflo.app.sec12.assignment.SlackRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08SlackAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec08SlackAssignment.class);

    public static void main(String[] args) {
        // slack room
        SlackRoom room = new SlackRoom("reactor");

        // create members
        SlackMember sam = new SlackMember("sam");
        SlackMember jake = new SlackMember("jake");
        SlackMember mike = new SlackMember("mike");

        // add 2 members
        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hola a todos...");
        Util.sleepSeconds(4);

        jake.says("¡Hey!");
        sam.says("Yo simplemente quiero decir hola...");
        Util.sleepSeconds(4);

        // add new member
        room.addMember(mike);
        mike.says("Hola chicos... encantado de estar aquí...");
    }
}
