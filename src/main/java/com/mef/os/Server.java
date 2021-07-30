package com.mef.os;

import java.util.stream.Collectors;

import io.javalin.Javalin;

public class Server
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start( 8080 );
        app.get( "/", ctx -> ctx.result( "Hello World" ) );
        app.get( "/args", ctx -> ctx.result( args.length > 0 ? args[0] : "None" ) );
        app.get( "/cp", ctx -> ctx.result( System.getProperties().entrySet().stream()
            .map( e -> e.getKey() + "=" + e.getValue() + "\n" ).collect( Collectors.toList() ).toString() ) );

        app.config.addStaticFiles( "/os/web" );

        // /deployments/os.jar
    }
}
