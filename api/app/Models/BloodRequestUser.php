<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class BloodRequestUser extends Model
{
    use HasFactory;

    protected $table = 'blood_requests';

    public $timestamps = false;

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
