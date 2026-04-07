package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Constants.Status;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import com.tekpyramid.DoctorFlow.Response.Success;
import com.tekpyramid.DoctorFlow.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/admin")
public class AdminController {

    @Autowired
    private LeaveService leaveService;

    // 🧾 1️⃣ View all pending leave requests
    @GetMapping("/pending-leaves")
    public ResponseEntity<Success> getAllPendingLeaves() {
        List<LeaveRequest> pendingLeaves = leaveService.getPendingLeaves();

        Success response = new Success();
        response.setMessage("Pending leave requests fetched successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(pendingLeaves);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ 2️⃣ Accept a leave request
    @PutMapping("/accept-leave/{leaveId}")
    public ResponseEntity<Success> acceptLeave(@PathVariable int leaveId) {
        LeaveRequest updatedLeave = leaveService.updateLeaveStatus(leaveId, Status.ACCEPTED);

        Success response = new Success();
        response.setMessage("Leave request accepted successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(updatedLeave);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ❌ 3️⃣ Reject a leave request
    @PutMapping("/reject-leave/{leaveId}")
    public ResponseEntity<Success> rejectLeave(@PathVariable int leaveId) {
        LeaveRequest updatedLeave = leaveService.updateLeaveStatus(leaveId, Status.REJECTED);

        Success response = new Success();
        response.setMessage("Leave request rejected successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(updatedLeave);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
