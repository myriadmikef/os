package com.mef.os;

import io.javalin.Javalin;

public class Server
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start( 8080 );
        app.get( "/", ctx -> ctx.result( "Hello World" ) );
        app.get( "/args", ctx -> ctx.result( args.length > 0 ? args[0] : "None" ) );
    }
}
