/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comet.test;

import com.sun.grizzly.comet.CometContext;
import com.sun.grizzly.comet.CometEngine;
import com.sun.grizzly.comet.CometEvent;
import com.sun.grizzly.comet.CometHandler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletContext;

/**
 *
 * @author Quicksort
 */
public class test extends HttpServlet {
    
    private final AtomicInteger counter = new AtomicInteger();
    private static final long serialVersionUID = 1L;
    private String contextPath = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        contextPath = context.getContextPath() + "/test";
        CometEngine engine = CometEngine.getEngine();
        CometContext cometContext = engine.register(contextPath);
        cometContext.setExpirationDelay(30*1000);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CounterHandler handler = new CounterHandler(Integer.parseInt(request.getParameter("t")));
        handler.attach(response);
        CometEngine engine = CometEngine.getEngine();
        CometContext context = engine.getCometContext(contextPath);
        context.addCometHandler(handler);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        counter.incrementAndGet();
        CometEngine engine = CometEngine.getEngine();
        CometContext<?> context = engine.getCometContext(contextPath);
        context.notify(counter.get());
        request.getRequestDispatcher("count.html").forward(request, response);
    }
    private class CounterHandler implements CometHandler<HttpServletResponse> {
        private HttpServletResponse response;
        private int token;
        public CounterHandler(int t) {
            this.token = t;
        }
        @Override
        public void attach(HttpServletResponse attachment) {
            this.response = attachment;
        }
        private void removeThisFromContext() throws IOException {
            response.getWriter().close();
            CometContext context = CometEngine.getEngine().getCometContext(contextPath);
            context.removeCometHandler(this);
        }

        @Override
        public void onEvent(CometEvent ce) throws IOException {
            if (CometEvent.NOTIFY == ce.getType() && (this.token % (Integer)ce.attachment() == 0)) {
		int count = counter.get();
		PrintWriter writer = response.getWriter();
		writer.write("<script type='text/javascript'>" + 
			"parent.counter.updateCount('" + count + "')" +
			"</script>\n");
		writer.flush();
		ce.getCometContext().resumeCometHandler(this);
            }
        }

        @Override
        public void onInitialize(CometEvent ce) throws IOException {
           
        }

        @Override
        public void onTerminate(CometEvent ce) throws IOException {
            removeThisFromContext();
        }

        @Override
        public void onInterrupt(CometEvent ce) throws IOException {
            removeThisFromContext();
        }
    }

}
