#include <iostream>
#include <fstream>
#include <filesystem>

using namespace std;

int main(int argc, char* argv[])
{
    if (argc != 2) {
        cerr << "Usage: " << argv[0] << " directory_path" << endl;
        return 1;
    }

    const string dir_path = argv[1];

    if (!filesystem::exists(dir_path) || !filesystem::is_directory(dir_path)) {
        cerr << "Invalid directory path: " << dir_path << endl;
        return 1;
    }

    for (const auto& entry : filesystem::directory_iterator(dir_path)) {
        if (entry.is_regular_file() && entry.path().extension() == ".rtf") {
            ifstream file(entry.path());
            string content((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
            file.close();

            for (string::size_type i = 0; i < content.length(); i++) {
                if (content[i] == '\\' && i + 1 < content.length() && content[i + 1] == 'i') {
                    content.replace(i, 2, "\\b");
                } else if (content[i] == '\\' && i + 1 < content.length() && content[i + 1] == 'b') {
                    content.replace(i, 2, "\\i");
                }
            }

            ofstream out(entry.path());
            out << content;
            out.close();
        }
    }

    return 0;
}