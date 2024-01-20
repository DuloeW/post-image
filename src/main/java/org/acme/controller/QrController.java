package org.acme.controller;

import java.io.IOException;
import java.io.OutputStream;

import org.acme.service.QrService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

@Path("/api/qr")
public class QrController {

    @Inject
    QrService qrService;
    
    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadQrCodes() {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException {
                try {
                    // Write the byte array to the output stream
                    output.write(qrService.getAllQrImageBuffers());
                } finally {
                    // Close the output stream
                    output.close();
                }
            }
        };

        // Return the file as a response with appropriate headers
        return Response.ok(stream)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qr-code.zip")
                .build();
    }
}
