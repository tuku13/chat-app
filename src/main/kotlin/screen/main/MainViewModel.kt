package screen.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.Room
import repository.RoomRepository

class MainViewModel(
    private val roomRepository: RoomRepository
) {
    private val _rooms: MutableStateFlow<List<Room>> = MutableStateFlow(emptyList())
    val rooms: StateFlow<List<Room>>
        get() = _rooms

    private val _selectedRoom: MutableStateFlow<Room?> = MutableStateFlow(null)
    val selectedRoom: StateFlow<Room?>
        get() = _selectedRoom

    suspend fun getRooms() {
        val rooms = roomRepository.getRooms()
        _rooms.emit(rooms)

        println("Rooms: ${_rooms.value.size}")
    }

    fun selectRoom(room: Room) {
        _selectedRoom.tryEmit(room)
    }

}