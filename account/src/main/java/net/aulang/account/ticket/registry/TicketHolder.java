package net.aulang.account.ticket.registry;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "ticket")
public class TicketHolder implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIELD_TICKETID = "ticketId";
    public static final String FIELD_DATA = "data";

    @Indexed(unique = true)
    private String ticketId;

    private byte[] data;

    @Indexed(expireAfterSeconds = 0)
    private long expireAt;

    public TicketHolder(String ticketId, byte[] data, long expireAt) {
        this.ticketId = ticketId;
        this.data = data;
        this.expireAt = expireAt;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(long expireAt) {
        this.expireAt = expireAt;
    }
}
