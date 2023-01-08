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
        $blood_request = DB::table('blood_requests')->join('users','blood_requests.puskesmas_id','=','users.id')
        ->where('scheduled_date', '>=', date_format(date_sub(date_create(date('Y-m-d')),date_interval_create_from_date_string("1 day")),'Y-m-d'))
        ->get();
        return response()->json($blood_request);
    }
}
