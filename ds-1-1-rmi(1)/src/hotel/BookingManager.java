package hotel;

import java.rmi.Remote;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingManager implements Remote {

	private Room[] rooms;

	public BookingManager() {
		this.rooms = initializeRooms();
	}

	public Set<Integer> getAllRooms() {
		Set<Integer> allRooms = new HashSet<Integer>();
		Room[] roomIterator = rooms;
		for (Room room : roomIterator) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		//implement this method
		Room[] roomIterator = rooms;
		for (Room room : roomIterator) {
			if (room.getRoomNumber().equals(roomNumber)) {
				List<BookingDetail> bookings = room.getBookings();
				for (BookingDetail detail : bookings) {
					if (detail.getDate().equals(date)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public void addBooking(BookingDetail bookingDetail) {
		Integer roomNumber = bookingDetail.getRoomNumber();
		if (!isRoomAvailable(roomNumber, bookingDetail.getDate())) {
			throw new IllegalArgumentException("Room not available at given date.");
		}
		Room[] roomIterator = rooms;
		for (Room room : roomIterator) {
			if (room.getRoomNumber().equals(roomNumber)) {
				List<BookingDetail> bookings = room.getBookings();
				bookings.add(bookingDetail);
				room.setBookings(bookings);
			}
		}
	}

	public Set<Integer> getAvailableRooms(LocalDate date) {
		//implement this method
		return null;
	}

	private static Room[] initializeRooms() {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);
		return rooms;
	}
}
