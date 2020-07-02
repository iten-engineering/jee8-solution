package ch.itenengineering.async.ejb;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsyncTestBean implements AsyncTest {

	@Override
	public void saveMsgSync(String msg) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(AsyncTestBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(new Date() + ": AsyncTestBean.saveMsgSync " + msg);
	}

	@Override
	@Asynchronous
	public void saveMsgAsync(String msg) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(AsyncTestBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(new Date() + ": AsyncTestBean.saveMsgAsync " + msg);
	}

	@Override
	@Asynchronous
	public Future<String> saveMsgAsyncFuture(String msg) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(AsyncTestBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(new Date() + ": AsyncTestBean.saveMsgAsyncFuture " + msg);

		return new  AsyncResult<String>("AsyncTestBean.saveMsgAsyncFuture saved message: " + msg);
	}

	@Override
	@Asynchronous
	public Future<String> saveMsgAsyncException(String msg) throws MyAsyncTestException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(AsyncTestBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(new Date() + ": AsyncTestBean.saveMsgAsyncException throw exception");

		throw new MyAsyncTestException("The message " + msg + " will not be saved");
	}

} // end
