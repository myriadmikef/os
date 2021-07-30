package com.mef.os;

import java.io.File;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Server
{
    private static final Logger logger = LoggerFactory.getLogger( Server.class );

    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start( 8080 );
        app.get( "/", ctx -> ctx.result( "Hello World" ) );
        app.get( "/args", ctx -> ctx.result( args.length > 0 ? args[0] : "None" ) );
        app.get( "/cp", ctx -> ctx.result( System.getProperties().entrySet().stream()
            .map( e -> e.getKey() + "=" + e.getValue() + "\n" ).collect( Collectors.toList() ).toString() ) );
        app.get( "/res", ctx -> Server.res( ctx ) );

        app.config.addStaticFiles( "/os/web" );

        // /deployments/os.jar
    }

    private static void res( Context ctx )
    {
        try
        {
            logger.info( "Hello World" );

            System.out.println( "Executing res" );
            String appConfigDirectory = System.getenv( "APP_CONFIG_DIR" );
            if ( appConfigDirectory != null )
            {
                System.out.println( "App Config Dir " + appConfigDirectory );
                File file = new File( appConfigDirectory, "db.properties" );
                ctx.result( file.getAbsolutePath() + " " + file.exists() + " " + file.canRead() );
            }
            else
            {
                ctx.result( "Resource not found" );
            }
        } catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
