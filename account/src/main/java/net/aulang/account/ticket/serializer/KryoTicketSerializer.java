package net.aulang.account.ticket.serializer;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.aulang.account.ticket.registry.TicketHolder;
import org.apereo.cas.ticket.Ticket;

public class KryoTicketSerializer {
    public static TicketHolder serializeTicket(Ticket ticket) {
        Output output = new Output(4096);
        Kryox.getInstance().writeClassAndObject(output, ticket);
        TicketHolder ticketHolder = new TicketHolder(ticket.getId(), output.toBytes(),
                ticket.getExpirationPolicy().getTimeToLive().intValue());
        output.close();
        return ticketHolder;
    }

    public static Ticket deserializeTicket(TicketHolder holder) {
        Input input = new Input(holder.getData());
        Ticket result = (Ticket) Kryox.getInstance().readClassAndObject(input);
        input.close();
        return result;
    }
}
