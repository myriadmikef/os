package com.mef.os;

import java.net.URL;
import java.util.stream.Collectors;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Server
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start( 8080 );
        app.get( "/", ctx -> ctx.result( "Hello World" ) );
        app.get( "/args", ctx -> ctx.result( args.length > 0 ? args[0] : "None" ) );
        app.get( "/cp", ctx -> ctx.result( System.getProperties().entrySet().stream()
            .map( e -> e.getKey() + "=" + e.getValue() + "\n" ).collect( Collectors.toList() ).toString() ) );
        app.get( "/res", ctx -> Server.db( ctx ) );

        app.config.addStaticFiles( "/os/web" );

        // /deployments/os.jar
    }

    private static void db( Context ctx )
    {
        try
        {
            System.out.println( "Executing db" );
            URL resource = Thread.currentThread().getContextClassLoader().getResource( "db.properties" );
            if ( resource != null )
            {
                ctx.result( resource.toString() );
            }
            else
                ctx.result( "Resource not found" );
        } catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
