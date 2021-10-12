package hotel;

import java.time.LocalDate;
import java.util.Set;

public interface IBookingManager {


    public Set<Integer> getAllRooms();

    public boolean isRoomAvailable(Integer roomNumber, LocalDate date);

    public void addBooking(BookingDetail bookingDetail);

    public Set<Integer> getAvailableRooms(LocalDate date);


}
