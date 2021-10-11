package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/async")
public class AsynTestRestController {

    /**
     * Asynchronous API used to get the commit status
     *
     *
     * @return returns the commit status
     */
    @Operation(description = "Asynchronous API used to get the commit status.", summary = "Asynchronous API to get commit status", tags = ("commitStatus"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Integration Program list")
    @GetMapping("/{status}")
    public @ResponseBody
    CompletableFuture<ResponseEntity> test(@PathVariable("status") String status) throws InterruptedException {
        return findByEmail(status).thenApplyAsync(ResponseEntity -> {
            return new ResponseEntity(new ResponseCode(status), HttpStatus.OK);
        });
    }

    //Please move this code to service
    @Async
    public CompletableFuture<Object> findByEmail(String status) throws InterruptedException {
        Object statusObj = status;
        String statusGot = "success";
        int i = 0;
        while (!status.equals(statusGot)) {
            statusGot = "success" + i;
            i++;
            System.out.println("statusGot :" + statusGot + "- Date:" + new Date());
            Thread.sleep(5000L);
        }

        return CompletableFuture.completedFuture(statusObj);
    }

}
