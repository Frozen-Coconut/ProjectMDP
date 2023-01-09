<?php

namespace App\Models;

use App\Models\User;
use App\Models\BloodRequest;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class BloodRequestUser extends Model
{
    use HasFactory;

    protected $table = 'blood_requests_users';

    public $timestamps = false;
    public $incrementing = true;

    protected $fillable = [
        'user_id',
        'blood_request_id',
        'status'
    ];

    public function blood_request() {
        return $this->belongsTo(BloodRequest::class, 'blood_request_id', 'id');
    }

    public function user() {
        return $this->belongsTo(User::class, 'user_id', 'id');
    }
}
