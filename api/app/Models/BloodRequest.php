<?php

namespace App\Models;

use App\Models\User;
use App\Models\BloodRequestUser;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class BloodRequest extends Model
{
    use HasFactory;

    protected $table = 'blood_requests';

    public $timestamps = false;
    public $incrementing = true;

    protected $fillable = [
        'blood_type',
        'scheduled_date',
        'puskesmas_id'
    ];

    public function puskesmas() {
        return $this->belongsTo(User::class, 'puskesmas_id', 'id');
    }

    public function user_accept() {
        return $this->hasMany(BloodRequestUser::class, 'blood_request_id', 'id');
    }
}
