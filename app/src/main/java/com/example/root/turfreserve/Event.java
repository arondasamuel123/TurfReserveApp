package com.example.root.turfreserve;

public class Event {
    String EventId;
    String Organizer;
    String Location;
    String Date;
    String Time;
    int Players;
    int Cost;

    public Event() {

    }


    public Event(String eventId, String organizer, String location, String date, String time, int players, int cost) {
        this.EventId = eventId;
        this.Organizer = organizer;
        this.Location = location;
        this.Date = date;
        this.Time = time;
        this.Players = players;
        this.Cost = cost;
    }

    public String getEventId() {
        return EventId;
    }

    public String getOrganizer() {
        return Organizer;
    }

    public String getLocation() {
        return Location;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public int getPlayers() {
        return Players;
    }

    public int getCost() {
        return Cost;
    }
}
