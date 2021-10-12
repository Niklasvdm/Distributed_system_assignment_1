package hotel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements Remote, IBookingManager{

	private Room[] rooms;

	public static void main(String[] args) throws RemoteException {
		System.setSecurityManager(null);
		System.setProperty("java.rmi.server.hostname","localhost");
		LocateRegistry.createRegistry(8080);
		BookingManager bm = new BookingManager();
		IBookingManager stub = (IBookingManager) UnicastRemoteObject.exportObject(bm,0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("bm-name", stub);
	}

	public BookingManager() {
		this.rooms = initializeRooms();
	}

	public Set<Integer> getAllRooms() {
		Set<Integer> allRooms = new HashSet<>();
		Room[] roomIterator = rooms;
		for (Room room : roomIterator) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
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

	public void addBooking(BookingDetail bookingDetail) throws IllegalArgumentException {
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
		HashSet<Integer> available = new HashSet<>();
		Room[] roomIterator = rooms;
		for (Room room : roomIterator) {
			List<BookingDetail> bookings = room.getBookings();
			boolean booked = false;
			for (BookingDetail booking : bookings) {
				if (booking.getDate().equals(date)) {
					booked = true;
				}
			}
			if (!booked) {
				available.add(room.getRoomNumber());
			}
		}
		return available;
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
