#include "Isle.h"

Isle::Isle(std::string name) : name(name)
{
}

const std::string& Isle::getName() const
{
    return this->name;
}
Item Isle::getItem()
{
    return item;
}

void Isle::setItem(Item item)
{
    this->item = item;
}

int Isle::getShaperCount()
{
    return shaperCount;
}

bool Isle::increaseShaperCount()
{
    if(shaperCount <= capacity){
        shaperCount++;
    }
    return shaperCount > capacity;
}

bool Isle::decreaseShaperCount()
{
    if(shaperCount > 0){
        shaperCount--;
    }
    return shaperCount <= 0;
}

bool Isle::operator==(const Isle &other) const
{
    return this->name == other.name;
}

bool Isle::operator<(const Isle &other) const
{
    return this->name < other.name;
}

bool Isle::operator>(const Isle &other) const
{
    return this->name > other.name;
}

// Implementation of readFromFile
std::vector<Isle *> Isle::readFromFile(const std::string &filename)
{
    std::vector<Isle *> isles;
    std::ifstream inputFile(filename);

    if (!inputFile.is_open())
    {
        std::cerr << "[Error] Could not open file: " << filename << std::endl;
        return isles;
    }

    std::string isleName;
    while (std::getline(inputFile, isleName))
    {
        if (!isleName.empty() && isleName[0] != '#')
        {
            isles.push_back(new Isle(isleName));
        }
    }

    inputFile.close();
    return isles;
}

std::ostream &operator<<(std::ostream &os, const Isle &p)
{
    // Prints to terminal with color
    // This function might cause some problems in terminals that are not Linux based
    // If so, comment out the colors while testing on local machine
    // But open them back up while submitting or using TurBo

    std::string einsteiniumColor = "\033[38;2;6;134;151m";
    std::string goldiumColor = "\033[38;2;255;198;5m";
    std::string amazoniteColor = "\033[38;2;169;254;255m";
    std::string resetColorTag = "\033[0m";

    if (p.item == EINSTEINIUM)
        return (os << einsteiniumColor << p.name << resetColorTag);
    if (p.item == GOLDIUM)
        return (os << goldiumColor << p.name << resetColorTag);
    if (p.item == AMAZONITE)
        return (os << amazoniteColor << p.name << resetColorTag);
    return (os << p.name);
}