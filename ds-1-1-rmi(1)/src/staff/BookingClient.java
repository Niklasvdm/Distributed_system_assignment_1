package staff;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.IBookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

	private IBookingManager bm = null;

	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient();
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			//Look up the registered remote instance
			System.setSecurityManager(null);
			Registry registry = LocateRegistry.getRegistry();
			bm = (IBookingManager) registry.lookup("bm-name");
			//bm = new BookingManager();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
		//Implement this method
		return bm.isRoomAvailable(roomNumber, date);
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws Exception, RemoteException {
		bm.addBooking(bookingDetail);
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date)throws RemoteException {
		return bm.getAvailableRooms(date);
	}

	@Override
	public Set<Integer> getAllRooms() throws RemoteException {
		return bm.getAllRooms();
	}
}
