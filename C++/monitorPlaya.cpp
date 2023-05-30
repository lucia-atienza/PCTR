// g++ -o main -pthread -std=c++11 monitorPlaya.cpp

#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>

class monitor
{
public:
    monitor() : actual_(0), max_(100){}
    void entrar()
    {
        std::unique_lock<std::mutex> lock(mtx_);
        while(actual_ == max_)
            noEntra_.wait(lock);
        actual_++;
        std::cout << "Numero actual de playeros: " << actual_  << std::endl;
    }
    void salir()
    {
        std::unique_lock<std::mutex> lock(mtx_);
        actual_--;
        noEntra_.notify_all();
        std::cout << "Numero actual de playeros: " << actual_  << std::endl;
    }
private:
    int actual_;
    int max_;
    std::mutex mtx_;
    std::condition_variable noEntra_;
};

void viaje(monitor& m)
{
    m.entrar();
    std::this_thread::sleep_for(std::chrono::seconds(1));
    m.salir();
}


int main(int argc, char const *argv[])
{
    monitor m;
    std::vector<std::thread> v;
    for(int i = 0; i < 200; i++)
        v.push_back(std::thread(viaje, std::ref(m)));
    for(int i = 0; i < 200; ++i) v[i].join();
    return 0;
}
