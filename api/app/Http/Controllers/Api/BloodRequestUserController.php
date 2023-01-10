<?php

namespace App\Http\Controllers\Api;

use App\Models\User;
use Illuminate\Http\Request;
use App\Models\BloodRequestUser;
use App\Http\Controllers\Controller;
use App\Models\BloodRequest;
use Faker\Core\Blood;

class BloodRequestUserController extends Controller
{
    public function Insert(Request $request)
    {
        $user = User::where('email','=',$request->email)->first();
        if ($user->accept()->where('blood_request_id','=',$request->blood_request_id)->exists()) {
            return response("Request sudah pernah diterima!");
        }
        else {
            BloodRequestUser::create([
                "user_id" => $user->id,
                "blood_request_id" => $request->blood_request_id,
                "status" => 0
            ]);
        }
        return response("Request berhasil diterima!");
    }

    public function Update(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('id', $request->id)->first()->update($request->all());
        return response()->json($blood_request_user);
    }

    public function Delete(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('id', $request->id)->first()->delete();
        return response()->json($blood_request_user);
    }

    public function Get(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('id', $request->id)->first();
        return response()->json($blood_request_user);
    }

    public function GetUser(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('user_id', $request->id)->get();
        return response()->json($blood_request_user);
    }

    public function GetReport(Request $request)
    {
        $data = [];
        $blood_requests = BloodRequest::where('puskesmas_id', $request->id)->get();
        foreach ($blood_requests as $blood_request) {
            foreach ($blood_request->user_accept()->where('status', 0)->get() as $blood_request_user) {
                $instance = [
                    'id' => $blood_request_user->id,
                    'user_name' => $blood_request_user->user->name,
                    'user_email' => $blood_request_user->user->email,
                    'blood_type' => $blood_request->blood_type,
                    'scheduled_date' => $blood_request->scheduled_date
                ];
                array_push($data, $instance);
            }
        }
        return response()->json($data);
    }

    public function GetHistory(Request $request) {
        $data = [];
        $user = User::where('email',$request->email)->first();
        foreach ($user->accept as $key => $value) {
            $instance = [
                "blood_type" => $value->blood_request->blood_type,
                "scheduled_date" => $value->blood_request->scheduled_date,
                "name" => $value->blood_request->puskesmas->name,
                "status" => $value->status
            ];
            array_push($data, $instance);
        }

        return response()->json($data);
    }
}
