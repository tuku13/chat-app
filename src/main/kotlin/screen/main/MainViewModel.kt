package screen.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.Room
import model.User
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
    }

    suspend fun addContact(contactInfo: User) {
        when (roomRepository.addContact(contactInfo)) {
            is NetworkResult.Success -> refreshRooms()
            else -> { }
        }
    }

    fun selectRoom(room: Room) {
        _selectedRoom.tryEmit(room)
    }

    suspend fun createRoom(roomName: String, users: List<User>) {
        if(roomName.isEmpty() || users.isEmpty()) {
            return
        }

        val users = users.map { it.id }
        roomRepository.createGroup(users, roomName)
        refreshRooms()
    }

    suspend fun leaveGroup(roomId: String) {
        roomRepository.leaveGroup(roomId)
        refreshRooms()
    }

    suspend fun joinRoom(roomId: String) {
        roomRepository.joinGroup(roomId)
        refreshRooms()
    }

}