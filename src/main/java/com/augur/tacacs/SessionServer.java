package com.augur.tacacs;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author Chris.Janicki@augur.com
 * Copyright 2016 Augur Systems, Inc.  All rights reserved.
 */
public class SessionServer extends Session
{
    private static final Logger log = Logger.getLogger(SessionServer.class);

	private final boolean debug;


	/** Server-side constructor */
	SessionServer(TAC_PLUS.AUTHEN.SVC svc, String port, String rem_addr, byte priv_lvl, TacacsReader tacacs, byte[] sessionID, boolean debug)
	{
		super(svc, port, rem_addr, priv_lvl, tacacs, sessionID);
		this.debug = debug;
	}


	/**
	 * TODO: IMPLEMENT SERVER RESPONSES TO CLIENT REQUESTS!!!
	 * @param p
	 * @throws IOException
	 */
	@Override synchronized void handlePacket(Packet p) throws IOException
	{
		super.handlePacket(p);
		Packet r;
		if (debug) { log.debug("TACACS rcv <-- "+p); }
		switch(p.header.type)
		{
			case AUTHEN:
				r = new AuthenReply
				(
					p.getHeader().next(TAC_PLUS.PACKET.VERSION.v13_0),
					TAC_PLUS.AUTHEN.STATUS.FAIL,
					FLAG_ZERO,
					"The AUTHENTICATION operation is not implemented.",
					"The AUTHENTICATION operation is not implemented."
				);
				tacacs.write(r);
				end(r);
				break;
			case AUTHOR:
				r = new AuthorReply
				(
					p.getHeader().next(TAC_PLUS.PACKET.VERSION.v13_0),
					TAC_PLUS.AUTHOR.STATUS.FAIL,
					"The AUTHORIZATION operation is not implemented.",
					"The AUTHORIZATION operation is not implemented.",
					null
				);
				tacacs.write(r);
				end(r);
				break;
			case ACCT:
				r = new AcctReply
				(
					p.getHeader().next(TAC_PLUS.PACKET.VERSION.v13_0),
					TAC_PLUS.ACCT.STATUS.ERROR,
					"The ACCOUNTING operation is not implemented.",
					"The ACCOUNTING operation is not implemented."
				);
				tacacs.write(r);
				end(r);
				break;
			default:
			    break;
		}
	}




}
