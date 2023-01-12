<?php

namespace App\Http\Controllers\Api;

use App\Models\BloodRequest;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;

class BloodRequestController extends Controller
{
    public function Insert(Request $request)
    {
        $blood_request = BloodRequest::create($request->all());
        return response()->json($blood_request);
    }

    public function Update(Request $request)
    {
        $blood_request = BloodRequest::where('id', $request->id)->first()->update($request->all());
        return response()->json($blood_request);
    }

    public function Delete(Request $request)
    {
        $blood_request = BloodRequest::where('id', $request->id)->first()->delete();
        return response()->json($blood_request);
    }

    public function Get(Request $request)
    {
        $blood_request = BloodRequest::where('id', $request->id)->first();
        return response()->json($blood_request);
    }

    public function GetAll(Request $request)
    {
        $blood_requests = BloodRequest::all();
        return response()->json($blood_requests);
    }

    public function GetAllBeforeDeadline(Request $request) {
        // $blood_request = DB::table('blood_requests')->join('users','blood_requests.puskesmas_id','=','users.id')
        // ->select('blood_requests.id AS id', 'blood_requests.blood_type AS blood_type', 'blood_requests.scheduled_date as scheduled_date', 'users.name AS name')
        // ->where('scheduled_date', '>=', date_format(date_sub(date_create(date('Y-m-d')),date_interval_create_from_date_string("1 day")),'Y-m-d'))
        // ->get();
        $data = [];
        $blood_requests = BloodRequest::where('scheduled_date', '>=', date_format(date_sub(date_create(date('Y-m-d')),date_interval_create_from_date_string("1 day")),'Y-m-d'))->get();
        foreach ($blood_requests as $blood_request) {
            $ketemu = false;
            foreach ($blood_request->user_accept as $user_accept) {
                if ($user_accept->user->email == $request->email) {
                    $ketemu = true;
                    break;
                }
            }
            if (!$ketemu) {
                $instance = [
                    "id" => $blood_request->id,
                    "blood_type" => $blood_request->blood_type,
                    "scheduled_date" => $blood_request->scheduled_date,
                    "name" => $blood_request->puskesmas->name
                ];
                array_push($data,$instance);
            }
        }
        return response()->json($data);
    }
}
