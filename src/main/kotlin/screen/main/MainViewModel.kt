package screen.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.Room
import model.UserInfo
import repository.RoomRepository
import util.NetworkResult

class MainViewModel(
    private val roomRepository: RoomRepository
) {
    private val _rooms: MutableStateFlow<List<Room>> = MutableStateFlow(emptyList())
    val rooms: StateFlow<List<Room>>
        get() = _rooms

    private val _selectedRoom: MutableStateFlow<Room?> = MutableStateFlow(null)
    val selectedRoom: StateFlow<Room?>
        get() = _selectedRoom

    suspend fun refreshRooms() {
        val rooms = roomRepository.getRooms()
        _rooms.emit(rooms)

        println("Rooms: ${_rooms.value.size}")
    }

    suspend fun addContact(contactInfo: UserInfo) {
        when (val response = roomRepository.addContact(contactInfo)) {
            is NetworkResult.Success -> refreshRooms()
            else -> { }
        }
    }

    fun selectRoom(room: Room) {
        _selectedRoom.tryEmit(room)
    }

}