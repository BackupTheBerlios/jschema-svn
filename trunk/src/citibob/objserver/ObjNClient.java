/* This class Copyright (c) 2005 by Robert P. Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */
package citibob.objserver;

import java.net.*;
import java.io.*;
//import gnu.net.local.*;

public class ObjNClient
{

// -------------------------------------------------------

String sockFilename;

public ObjNClient() {}


public void close() {}

public ObjNClient(String sockFilename)
{
	this.init(sockFilename);
}
public void init(String sockFilename)
{
	this.sockFilename = sockFilename;
}

public SendQueryReturn sendQuery(ObjQuery q) throws IOException
	{ return sendQuery(sockFilename, q); }

public InputStream sendSelectQuery(ObjQuery q) throws IOException
	{ return sendQuery(sockFilename, q).in; }

public OutputStream sendUpdateQuery(ObjQuery q) throws IOException
	{ return sendQuery(sockFilename, q).out; }

public Object sendSelectQueryRet(ObjQuery q) throws IOException, ClassNotFoundException
{
	ObjectInputStream ooin = new ObjectInputStream(sendQuery(sockFilename, q).in);
	Object ret = ooin.readObject();
	ooin.close();
	return ret;
}

public static SendQueryReturn sendQuery(String sockFilename, ObjQuery q)
throws IOException
{
	SendQueryReturn ret = new SendQueryReturn();

	NamedSocket socket = new NamedSocket(sockFilename);
	ret.out = socket.getOutputStream();
	ObjectOutputStream oout = new ObjectOutputStream(ret.out);
	oout.writeObject(q);
	oout.flush();
	// For some reason, this socket.getInputStream() must come AFTER the
	// above writeObject().
	ret.in = socket.getInputStream();
	return ret;
}
// --------------------------------------------------------
/** Structure allowing the return of two objects from the {@link ViperClient#sendQuery} method.
*/
public static class SendQueryReturn
{
	/** The stream from the server to the client. */
	public InputStream in;
	/** The stream from the client to the server. */
	public OutputStream out;
}

}
